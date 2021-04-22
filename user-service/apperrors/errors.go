package apperrors

import (
	"errors"
	"fmt"
	"net/http"
	"time"
)

type Type string

const (
	Unauthorization      Type = "UNAUTHORIZATION"
	BadRequest           Type = "BAD_REQUEST"
	Conflict             Type = "CONFLICT"
	Internal             Type = "INTERNAL"
	NotFound             Type = "NOT_FOUND"
	ServiceUnavailable   Type = "SERVICE_UNAVAILABLE"
	UnSupportedMediaType Type = "UN_SUPPORTED_MEDIATYPE"
)

type Error struct {
	TimeStemp  time.Time `json:"timestamp"`
	StatusCode int       `json:"status"`
	Type       Type      `json:"error"`
	Message    string    `json:"message"`
}

// requiers to implement error interface
func (e *Error) Error() string {
	return e.Message
}

// convert any error to http status code
func Status(err error) int {
	var e *Error
	if errors.As(err, &e) {
		return e.StatusCode
	}
	return http.StatusInternalServerError
}

// factory for Unauthorized error
// reason - explain why is unauthorized
func NewUnauthorized(reason string) *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusUnauthorized,
		Type:       Unauthorization,
		Message:    reason,
	}
}

// factory for bad request error
// reson - explain what is wrong
func NewBadRequest(reason string) *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusBadRequest,
		Type:       BadRequest,
		Message:    fmt.Sprintf("Bad request. Reason: %v", reason),
	}
}

// factory for conflict error
// name - name of parametr witch raise conflict error
// value - value with raise conflict error
func NewConflict(name string, value string) *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusConflict,
		Type:       Conflict,
		Message:    fmt.Sprintf("resource: %v with value: %v already exists", name, value),
	}
}

// factory for internal error
func NewInternal() *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusInternalServerError,
		Type:       Internal,
		Message:    "Internal server error.",
	}
}

// factory for not found error
// name - name of resource wich is not found
// value - vlue of resource wich is not found
func NewNotFound(name string, value string) *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusNotFound,
		Type:       NotFound,
		Message:    fmt.Sprintf("resource: %v with value: %v not found", name, value),
	}
}

// factory for Service unavailable error
func NewServiceUnavailable() *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusServiceUnavailable,
		Type:       ServiceUnavailable,
		Message:    "Service unavailable or timed out",
	}
}

// factory for unsupported madia type error
// reson explain why and wich resources an suported
func NewUnsupportedMediaType(reason string) *Error {
	return &Error{
		TimeStemp:  time.Now(),
		StatusCode: http.StatusUnsupportedMediaType,
		Type:       UnSupportedMediaType,
		Message:    reason,
	}
}
