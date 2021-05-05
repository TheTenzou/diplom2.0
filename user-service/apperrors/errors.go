package apperrors

import (
	"errors"
	"fmt"
	"net/http"
	"time"
)

type Type string

const (
	Unauthorized         Type = "UNAUTHORIZED"
	BadRequest           Type = "BAD_REQUEST"
	Conflict             Type = "CONFLICT"
	Internal             Type = "INTERNAL"
	NotFound             Type = "NOT_FOUND"
	ServiceUnavailable   Type = "SERVICE_UNAVAILABLE"
	UnSupportedMediaType Type = "UN_SUPPORTED_MEDIA_TYPE"
)

type Error struct {
	Timestamp  time.Time `json:"timestamp"`
	StatusCode int       `json:"status"`
	Type       Type      `json:"error"`
	Message    string    `json:"message"`
}

// requires to implement error interface
func (e *Error) Error() string {
	return e.Message
}

// Status convert any error to http status code
func Status(err error) int {
	var e *Error
	if errors.As(err, &e) {
		return e.StatusCode
	}
	return http.StatusInternalServerError
}

func ConvertToAppError(err error) *Error {
	var e *Error
	if errors.As(err, &e) {
		return e
	}
	return NewInternal()
}

// NewUnauthorized factory for Unauthorized error
// reason - explain why is unauthorized
func NewUnauthorized(reason string) *Error {
	return &Error{
		Timestamp:  time.Now(),
		StatusCode: http.StatusUnauthorized,
		Type:       Unauthorized,
		Message:    reason,
	}
}

// NewBadRequest factory for bad request error
// reason - explain what is wrong
func NewBadRequest(reason string) *Error {
	return &Error{
		Timestamp:  time.Now(),
		StatusCode: http.StatusBadRequest,
		Type:       BadRequest,
		Message:    fmt.Sprintf("Bad request. Reason: %v", reason),
	}
}

// NewConflict factory for conflict error
// name - name of parameter witch raise conflict error
// value - value with raise conflict error
func NewConflict(name string, value string) *Error {
	return &Error{
		Timestamp:  time.Now(),
		StatusCode: http.StatusConflict,
		Type:       Conflict,
		Message:    fmt.Sprintf("resource: %v with value: %v already exists", name, value),
	}
}

// NewInternal factory for internal error
func NewInternal() *Error {
	return &Error{
		Timestamp:  time.Now(),
		StatusCode: http.StatusInternalServerError,
		Type:       Internal,
		Message:    "Internal server error.",
	}
}

// NewNotFound factory for not found error
// name - name of resource which is not found
// value - value of resource which is not found
func NewNotFound(name string, value string) *Error {
	return &Error{
		Timestamp:  time.Now(),
		StatusCode: http.StatusNotFound,
		Type:       NotFound,
		Message:    fmt.Sprintf("resource: %v with value: %v not found", name, value),
	}
}

// NewServiceUnavailable factory for Service unavailable error
func NewServiceUnavailable() *Error {
	return &Error{
		Timestamp:  time.Now(),
		StatusCode: http.StatusServiceUnavailable,
		Type:       ServiceUnavailable,
		Message:    "Service unavailable or timed out",
	}
}

// NewUnsupportedMediaType factory for unsupported media type error
// reason explain why and which resources an supported
func NewUnsupportedMediaType(reason string) *Error {
	return &Error{
		Timestamp:  time.Now(),
		StatusCode: http.StatusUnsupportedMediaType,
		Type:       UnSupportedMediaType,
		Message:    reason,
	}
}
