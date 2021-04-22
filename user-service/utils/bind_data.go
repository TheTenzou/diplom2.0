package utils

import (
	"fmt"
	"log"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
)

type invalidArgument struct {
	Field string `json:"field"`
	Value string `json:"value"`
	Tag   string `json:"tag"`
	Param string `json:"param"`
}

// bind data from context to request structure
// if error occurred returns response with error
func BindData(ctx *gin.Context, request interface{}) bool {

	if ctx.ContentType() != "application/json" {
		message := fmt.Sprintf("%s only accept Content-Type application/json", ctx.FullPath())

		err := apperrors.NewUnsupportedMediaType(message)

		ctx.JSON(err.StatusCode, err)

		return false
	}

	if err := ctx.BindJSON(request); err != nil {
		log.Printf("Error binding data: %v\n", err)

		bindingsErrors := getBindigError(err)

		if bindingsErrors != nil {

			err := apperrors.NewBadRequest("Invalid request paramtrs. See invalidArguments")

			ctx.JSON(err.StatusCode, gin.H{
				"error":           err,
				"invalidArgumets": bindingsErrors,
			})

			return false
		}

		err := apperrors.NewInternal()

		ctx.JSON(err.StatusCode, err)

		return false
	}

	return true
}

// return list of binding errors
// requires to make sansable error discription
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
