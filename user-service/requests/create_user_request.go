package requests

type CreateUserRequest struct {
	Login    string `json:"login" binding:"required"`
	Password string `json:"password" binding:"required,gte=6,lte=30"`
	Name     string `json:"name" binding:"omitempty"`
}
