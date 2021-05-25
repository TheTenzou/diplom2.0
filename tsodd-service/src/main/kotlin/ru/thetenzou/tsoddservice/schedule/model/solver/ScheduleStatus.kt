package ru.thetenzou.tsoddservice.schedule.model.solver

/**
 * A ScheduleStatus describe is schedule in the process of generating
 */
enum class ScheduleStatus {

    /**
     * Schedule already generated
     */
    GENERATED,

    /**
     * Schedule currently generating
     */
    GENERATING,
}