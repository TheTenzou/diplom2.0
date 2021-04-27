package handler

import (
	"log"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/TheTenzou/gis-diplom/user-service/requests"
	"github.com/TheTenzou/gis-diplom/user-service/utils"
	"github.com/gin-gonic/gin"
)

type authHandler struct {
	AuthService interfaces.AuthService
}

type AuthHandlerConfig struct {
	AuthService interfaces.AuthService
}

// init user routs
func InitAuthHandler(router *gin.Engine, config AuthHandlerConfig) {

	handler := authHandler{
		AuthService: config.AuthService,
	}

	group := router.Group("/api/users/v1/auth")

	group.POST("/login", handler.login)
}

func (h *authHandler) login(ctx *gin.Context) {
	var request requests.LoginRequest

	if ok := utils.BindData(ctx, &request); !ok {
		return
	}

	user := model.User{
		Login:    request.Login,
		Password: request.Password,
	}

	requestCtx := ctx.Request.Context()

	tokenPair, err := h.AuthService.Login(requestCtx, user)
	if err != nil {
		log.Printf("faild to authenticate user: %v", err)
		ctx.JSON(apperrors.Status(err), apperrors.ConvertToAppError(err))
		return
	}

	ctx.JSON(200, tokenPair)
}
