import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import resources.SchedulerCalendar;
import resources.WorkPeriods;
import resources.WorkPeriod;
import resources.Event;
import resources.Schedule;

public class Main {
    public static void main(String[] args) {

        Clock testClock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
        // create a schedule calendar
        SchedulerCalendar calendar = new SchedulerCalendar();
        // add some tasks to the schedule calendar
        calendar.addTask("Answer urgent e-mail", 1, 0);
        calendar.addTask("Write deployment report", 4, 0);
        calendar.addTask("Plan security configuration", 4, 0);

        // add some work periods to the schedule calendar
        LocalDate clockDate = LocalDate.now(testClock);
        LocalDate mondayStart = clockDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        List<WorkPeriod> periods = WorkPeriods.generateWorkPeriods(mondayStart, 3);
        calendar.addWorkPeriods(periods);

        // add an event to the schedule calendar
        ZonedDateTime meetingStartTime = ZonedDateTime.of(
                mondayStart, LocalTime.of(7, 30), ZoneId.of("America/New_York"));
        Event nyPlanningMeeting = Event.of(meetingStartTime, Duration.ofHours(1), "New York Planning");
        calendar.addEvent(nyPlanningMeeting);

        // create a working schedule
        Schedule schedule = calendar.createSchedule(mondayStart, ZoneId.of("Europe/London"));

        // and print it out
        System.out.println(schedule);
    }
}
