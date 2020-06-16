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
        Collection<String> mandatoryAttendees = request.getAttendees();

        Collection<TimeRange> availableTimes = new ArrayList<>();

        if (meetingDuration > TimeRange.WHOLE_DAY.duration()) {
            return availableTimes;
        }
        if (events.isEmpty()) {
            availableTimes.add(TimeRange.WHOLE_DAY);
            return availableTimes;
        }

        for (Event event : events) {
            TimeRange eventRange = event.getWhen();
            Set<String> eventAttendees = event.getAttendees();

            boolean hasNoCommonAttendees = Collections.disjoint(mandatoryAttendees, eventAttendees);
             
            // if no event attendees need to go to requested meeting
            if (hasNoCommonAttendees) {
                if (availableTimes.isEmpty()) {
                    availableTimes.add(TimeRange.WHOLE_DAY);
                }
                continue;
            }

            // creates TimeRange available before and after event
            TimeRange availableBefore = 
                TimeRange.fromStartEnd(TimeRange.START_OF_DAY, eventRange.start(), /* inclusive= */ false);
            TimeRange availableAfter = 
                TimeRange.fromStartEnd(eventRange.end(), TimeRange.END_OF_DAY, /* inclusive= */ true);
            
            availableTimes = returnOverlaps(availableBefore, availableAfter, availableTimes, meetingDuration);
        }

        return availableTimes;
    }

    private Collection<TimeRange> returnOverlaps(TimeRange beforeTimeRange, TimeRange afterTimeRange, Collection<TimeRange> availableTimes, long meetingDuration) {
        if (availableTimes.isEmpty()) {
            availableTimes.add(beforeTimeRange);
            availableTimes.add(afterTimeRange);
            return availableTimes;
        }

        Collection<TimeRange> overlaps =  new ArrayList<>();
        for (TimeRange range : availableTimes) {
            findOverlap(range, beforeTimeRange, overlaps, meetingDuration);
            findOverlap(range, afterTimeRange, overlaps, meetingDuration);
        }

        return overlaps;
    }

    private void findOverlap(TimeRange alreadyAvailableRange, TimeRange eventAvailableRange, Collection<TimeRange> overlaps, long meetingDuration) {
        if (alreadyAvailableRange.overlaps(eventAvailableRange)) {
            int latestStart = alreadyAvailableRange.start() < eventAvailableRange.start() ? eventAvailableRange.start() : alreadyAvailableRange.start();
            int earliestEnd = alreadyAvailableRange.end() < eventAvailableRange.end() ? alreadyAvailableRange.end() : eventAvailableRange.end();
            TimeRange overlap = TimeRange.fromStartEnd(latestStart, earliestEnd, /* inclusive= */ false);
            if (overlap.duration() >= meetingDuration) {
                overlaps.add(overlap);
            }
        }
    }
}
