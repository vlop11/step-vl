// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        final long meetingDuration = request.getDuration(); // in minutes
        Collection<String> requestAttendees = request.getAttendees();

        Collection<TimeRange> availableTimes = new ArrayList<>();

        if (meetingDuration > TimeRange.WHOLE_DAY.duration()) {
            return availableTimes;
        }
        if (events.size() == 0) {
            availableTimes.add(TimeRange.WHOLE_DAY);
            return availableTimes;
        }

        for (Event event : events) {
            TimeRange when = event.getWhen();
            Set<String> eventAttendees = event.getAttendees();

            boolean hasNoCommonAttendees = Collections.disjoint(requestAttendees, eventAttendees);
             
            // if no event attendees need to go to requested meeting
            if (hasNoCommonAttendees) {
                if (availableTimes.size() == 0) {
                    availableTimes.add(TimeRange.WHOLE_DAY);
                }
                continue;
            }

            // creates TimeRange available before and after event
            TimeRange availableBefore = TimeRange.fromStartEnd(TimeRange.START_OF_DAY, when.start(), false);
            TimeRange availableAfter = TimeRange.fromStartEnd(when.end(), TimeRange.END_OF_DAY, true);
            
            availableTimes = returnOverlaps(availableBefore, availableAfter, availableTimes, meetingDuration);
        }

        return availableTimes;
    }

    private Collection<TimeRange> returnOverlaps(TimeRange before, TimeRange after, Collection<TimeRange> available, long duration) {
        if (available.size() == 0) {
            available.add(before);
            available.add(after);
            return available;
        }

        Collection<TimeRange> overlaps =  new ArrayList<>();
        for (TimeRange range : available) {

            if (range.overlaps(before)) {
                int latestStart = range.start() < before.start() ? before.start() : range.start();
                int earliestEnd = range.end() < before.end() ? range.end() : before.end();
                TimeRange overlap = TimeRange.fromStartEnd(latestStart, earliestEnd, false);
                if (overlap.duration() >= duration) {
                    overlaps.add(overlap);
                }
            }

            if (range.overlaps(after)) {
                int latestStart = range.start() < after.start() ? after.start() : range.start();
                int earliestEnd = range.start() < after.end() ? range.end() : after.start();
                TimeRange overlap = TimeRange.fromStartEnd(latestStart, earliestEnd, false);
                if (overlap.duration() >= duration) {
                    overlaps.add(overlap);
                }
            }
        }

        return overlaps;
    }
}
