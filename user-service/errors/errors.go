package errors

import (
	"errors"
	"fmt"
	"net/http"
	"time"
)

type Type string

const (
	Unauthorization    Type = "UNAUTHORIZATION"
	BadRequest         Type = "BAD_REQUEST"
	Conflict           Type = "CONFLICT"
	Internal           Type = "INTERNAL"
	NotFound           Type = "NOT_FOUND"
	ServiceUnavailable Type = "SERVICE_UNAVAILABLE"
)

type Error struct {
	TimeStemp  time.Time `json:"timestamp"`
	StatusCode int       `json:"status"`
	Type       Type      `json:"type"`
	Message    string    `json:"message"`
}

func (e *Error) Error() string {
	return e.Message
}

func (e *Error) Status() int {
	return e.StatusCode
}

func Status(err error) int {
	var e *Error
	if errors.As(err, &e) {
		return e.StatusCode
	}
	return http.StatusInternalServerError
}

func NewAuthorization(reason string) *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusUnauthorized,
		Type:       Unauthorization,
		Message:    reason,
	}
}

func NewBadRequest(reason string) *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusBadRequest,
		Type:       BadRequest,
		Message:    fmt.Sprintf("Bad request. Reason: %v", reason),
	}
}

func NewConflict(name string, value string) *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusConflict,
		Type:       Conflict,
		Message:    fmt.Sprintf("resource: %v with value: %v already exists", name, value),
	}
}

func NewInternal() *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusInternalServerError,
		Type:       Internal,
		Message:    "Internal server error.",
	}
}

func NewNotFound(name string, value string) *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusNotFound,
		Type:       NotFound,
		Message:    fmt.Sprintf("resource: %v with value: %v not found", name, value),
	}
}

func NewServiceUnavailable() *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusServiceUnavailable,
		Type:       ServiceUnavailable,
		Message:    "Service unavailable or timed out",
	}
}
