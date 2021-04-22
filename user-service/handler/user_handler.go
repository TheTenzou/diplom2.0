package handler

import (
	"log"
	"net/http"

	"github.com/TheTenzou/diplom2.0/user-service/apperrors"
	"github.com/TheTenzou/diplom2.0/user-service/interfaces"
	"github.com/TheTenzou/diplom2.0/user-service/model"
	"github.com/TheTenzou/diplom2.0/user-service/requests"
	"github.com/TheTenzou/diplom2.0/user-service/utils"
	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type userHandler struct {
	UserService interfaces.UserService
}

// init user routs
func InitUserHandler(router *gin.Engine, userService interfaces.UserService) {
	handler := userHandler{
		UserService: userService,
	}

	group := router.Group("/api/users/v1")

	group.GET("/user/:userID", handler.getUser)
	group.GET("/users", handler.getUsers)
	group.PUT("/user", handler.createUser)
	group.PATCH("/user", handler.updateUser)
	group.DELETE("/user/:userID", handler.deleteUser)

}

// handle request of user
func (h *userHandler) getUser(ctx *gin.Context) {
	userID, err := primitive.ObjectIDFromHex(ctx.Params.ByName("userID"))
	if err != nil {
		log.Printf("faild to parse userID: %v", err)
		ctx.JSON(apperrors.Status(err), apperrors.ConvertToAppError(err))
		return
	}
	requestCtx := ctx.Request.Context()
	user, err := h.UserService.FindByID(requestCtx, userID)
	if err != nil {
		log.Printf("faild to get user: %v", err)
		ctx.JSON(apperrors.Status(err), apperrors.ConvertToAppError(err))
		return
	}

	ctx.JSON(http.StatusOK, user)
}

// handle paginated users list request
func (h *userHandler) getUsers(ctx *gin.Context) {
	ctx.JSON(200, gin.H{
		"user": "all users",
	})
}

// handle creation of user
func (h *userHandler) createUser(ctx *gin.Context) {
	var request requests.CreateUserRequest

	if ok := utils.BindData(ctx, &request); !ok {
		return
	}

	user := model.User{
		Login:    request.Login,
		Password: request.Password,
		Name:     request.Name,
	}

	requestCtx := ctx.Request.Context()

	createdUser, err := h.UserService.Create(requestCtx, user)

	if err != nil {
		log.Printf("failed to create user: %v\n", err)
		ctx.JSON(apperrors.Status(err), apperrors.ConvertToAppError(err))
		return
	}

	ctx.JSON(http.StatusOK, createdUser)
}

// handle update request of user
func (h *userHandler) updateUser(ctx *gin.Context) {
	var request requests.UpdateUserRequest

	if ok := utils.BindData(ctx, &request); !ok {
		return
	}

	user := model.User{
		ID:       request.ID,
		Login:    request.Login,
		Password: request.Password,
		Name:     request.Name,
	}

	requestCtx := ctx.Request.Context()

	err := h.UserService.Update(requestCtx, user)

	if err != nil {
		log.Printf("failed to update user: %v\n", err)
		ctx.JSON(apperrors.Status(err), apperrors.ConvertToAppError(err))
		return
	}

	ctx.JSON(http.StatusOK, user)
}

// handle deletion of user
func (h *userHandler) deleteUser(ctx *gin.Context) {
	userID, err := primitive.ObjectIDFromHex(ctx.Params.ByName("userID"))
	if err != nil {
		log.Printf("faild to parse userID: %v", err)
		ctx.JSON(apperrors.Status(err), apperrors.ConvertToAppError(err))
		return
	}

	requestCtx := ctx.Request.Context()
	deletedUser, err := h.UserService.Delete(requestCtx, userID)

	if err != nil {
		log.Printf("failed to delete user: %v\n", err)
		ctx.JSON(apperrors.Status(err), apperrors.ConvertToAppError(err))
		return
	}

	ctx.JSON(http.StatusOK, deletedUser)
}
