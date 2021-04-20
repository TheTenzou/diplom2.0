package handler

import (
	"log"

	"github.com/TheTenzou/diplom2.0/user-service/interfaces"
	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type userHandler struct {
	UserService interfaces.UserService
}

func InitUserHandler(router *gin.Engine, userService *interfaces.UserService) {
	handler := userHandler{
		UserService: *userService,
	}

	group := router.Group("/api/users/v1")

	group.GET("/user/:userID", handler.getUser)
	group.GET("/users")
	group.PUT("/user")
	group.PATCH("/user")
	group.DELETE("/user")

}

func (h *userHandler) getUser(ctx *gin.Context) {
	userID, err := primitive.ObjectIDFromHex(ctx.Params.ByName("userID"))
	if err != nil {
		log.Printf("faild to parse userID: %v", err)

		ctx.JSON(500, gin.H{
			"err": "faild to extract id",
		})

		return
	}
	requestCtx := ctx.Request.Context()
	user, err := h.UserService.FindByID(requestCtx, userID)
	if err != nil {
		log.Panicf("failed to fetch user: %v\n%v", userID, err)

		ctx.JSON(500, gin.H{
			"err": "faild to fetch user",
		})

		return
	}

	ctx.JSON(200, gin.H{
		"user": user,
	})

}
