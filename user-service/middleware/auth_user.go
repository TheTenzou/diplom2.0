package middleware

import (
	"strings"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
)

type authHeader struct {
	Token string `header:"Authorization"`
}

type invalidArgument struct {
	Field string `json:"field"`
	Value string `json:"value"`
	Tag   string `json:"tag"`
	Param string `json:"param"`
}

// AuthUser extracts a user from the Authorization header
// It sets the user to the context if the user exists
func AuthUser(authService interfaces.AuthService) gin.HandlerFunc {
	return func(ginContext *gin.Context) {
		header := authHeader{}

		if err := ginContext.ShouldBindHeader(&header); err != nil {

			if errs, ok := err.(validator.ValidationErrors); ok {

				invalidArgs := getInvalidArguments(errs)
				err := apperrors.NewBadRequest("Invalid request parameters. See invalidArgs")

				ginContext.JSON(err.StatusCode, gin.H{
					"error":       err,
					"invalidArgs": invalidArgs,
				})
				ginContext.Abort()
				return
			}

			error := apperrors.NewInternal()
			ginContext.JSON(error.StatusCode, error)
			ginContext.Abort()
			return
		}

		idTokenHeader := strings.Split(header.Token, "Bearer ")

		if len(idTokenHeader) < 2 {
			err := apperrors.NewUnauthorized("Must provide Authorization header with format `Bearer {token}`")

			ginContext.JSON(err.StatusCode, err)
			ginContext.Abort()
			return
		}

		user, err := authService.ValidateAccessToken(idTokenHeader[1])

		if err != nil {
			err := apperrors.NewUnauthorized("Provided token is invalid")
			ginContext.JSON(err.StatusCode, err)
			ginContext.Abort()
			return
		}

		ginContext.Set("user", user)

		ginContext.Next()
	}
}

func getInvalidArguments(errs validator.ValidationErrors) []invalidArgument {
	var invalidArgs []invalidArgument

	for _, err := range errs {
		invalidArgs = append(invalidArgs, invalidArgument{
			err.Field(),
			err.Value().(string),
			err.Tag(),
			err.Param(),
		})
	}
	return invalidArgs
}
