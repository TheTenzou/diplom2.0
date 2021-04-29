package handler

import (
	"log"
	"net/http"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/middleware"
	"github.com/TheTenzou/gis-diplom/user-service/model"
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
	group.POST("/refresh", handler.refreshTokens)
	group.POST("/logout", middleware.AuthUser(handler.AuthService), handler.logout)
}

// structure for holding and validation incoming login user request
// input argument of bindData
type loginRequest struct {
	Login    string `json:"login" binding:"required,gte=5,lte=32"`
	Password string `json:"password" binding:"required,gte=5,lte=32"`
}

func (h *authHandler) login(ctx *gin.Context) {
	var request loginRequest

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

	ctx.JSON(http.StatusOK, tokenPair)
}

// structure for holding and validation incoming login user request
// input argument of bindData
type refreshRequest struct {
	RefreshToken string `json:"refreshToken" binding:"required"`
}

func (h *authHandler) refreshTokens(ctx *gin.Context) {
	var request refreshRequest

	if ok := utils.BindData(ctx, &request); !ok {
		return
	}

	requestContext := ctx.Request.Context()

	tokenPair, err := h.AuthService.RefreshTokens(requestContext, request.RefreshToken)
	if err != nil {
		log.Printf("failed to refresh tokens: %v", err)
		ctx.JSON(apperrors.Status(err), apperrors.ConvertToAppError(err))
		return
	}

	ctx.JSON(http.StatusOK, tokenPair)
}

func (h *authHandler) logout(ctx *gin.Context) {
	user := ctx.MustGet("user")

	requestContext := ctx.Request.Context()

	err := h.AuthService.BlackListTokens(requestContext, user.(model.User).ID)
	if err != nil {
		log.Printf("Failed to blacklist refresh token: %v", err)
		ctx.JSON(apperrors.Status(err), apperrors.ConvertToAppError(err))
		return
	}
	ctx.JSON(http.StatusOK, gin.H{
		"message": "user logout successfully",
	})
}
