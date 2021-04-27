package requests

type LoginRequest struct {
	Login    string `json:"login" binding:"required,gte=6,lte=32"`
	Password string `json:"password" binding:"required,gte=6,lte=30"`
}
