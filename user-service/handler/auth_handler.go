package handler

import (
	"log"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
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
}

// structure for holding and validation incoming login user request
// input argument of bindData
type LoginRequest struct {
	Login    string `json:"login" binding:"required,gte=6,lte=32"`
	Password string `json:"password" binding:"required,gte=6,lte=30"`
}

func (h *authHandler) login(ctx *gin.Context) {
	var request LoginRequest

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

func (h *authHandler) refreshTokens(ctx *gin.Context) {

}
