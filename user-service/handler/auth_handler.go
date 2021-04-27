package handler

import (
	"github.com/TheTenzou/gis-diplom/user-service/requests"
	"github.com/TheTenzou/gis-diplom/user-service/utils"
	"github.com/gin-gonic/gin"
)

type authHandler struct {
}

type AuthHandlerConfig struct {
}

// init user routs
func InitAuthHandler(router *gin.Engine, config AuthHandlerConfig) {

	handler := authHandler{}

	group := router.Group("/api/users/v1/auth")

	group.POST("/login", handler.login)
}

func (h *authHandler) login(ctx *gin.Context) {
	var request requests.LoginRequest

	if ok := utils.BindData(ctx, &request); !ok {
		return
	}

	ctx.JSON(200, request)
}
