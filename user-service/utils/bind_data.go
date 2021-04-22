package utils

import (
	"fmt"
	"log"

	"github.com/TheTenzou/diplom2.0/user-service/apperrors"
	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
)

type invalidArgument struct {
	Field string `json:"field"`
	Value string `json:"value"`
	Tag   string `json:"tag"`
	Param string `json:"param"`
}

func BindData(ctx *gin.Context, request interface{}) bool {

	if ctx.ContentType() != "application/json" {
		message := fmt.Sprintf("%s only accept Content-Type application/json", ctx.FullPath())

		err := apperrors.NewUnsupportedMediaType(message)

		ctx.JSON(err.Status(), err)

		return false
	}

	if err := ctx.BindJSON(request); err != nil {
		log.Printf("Error binding data: %v\n", err)

		bindingsErrors := getBindigError(err)

		if bindingsErrors != nil {

			err := apperrors.NewBadRequest("Invalid request paramtrs. See invalidArguments")

			ctx.JSON(err.Status(), gin.H{
				"error":           err,
				"invalidArgumets": bindingsErrors,
			})

			return false
		}

		err := apperrors.NewInternal()

		ctx.JSON(err.Status(), err)

		return false
	}

	return true
}

func getBindigError(err error) []invalidArgument {

	if errors, ok := err.(validator.ValidationErrors); ok {

		var invalidArgs []invalidArgument

		for _, err := range errors {

			invalidArgs = append(
				invalidArgs,
				invalidArgument{
					err.Field(),
					err.Value().(string),
					err.Tag(),
					err.Param(),
				},
			)
		}

		return invalidArgs
	}

	return nil
}
