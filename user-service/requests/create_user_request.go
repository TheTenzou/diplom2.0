package requests

// structure for holding and validation incoming create user request
// input argument of bindData
type CreateUserRequest struct {
	Login    string   `json:"login" binding:"required,gte=6,lte=32"`
	Password string   `json:"password" binding:"required,gte=6,lte=30"`
	Name     string   `json:"name" binding:"omitempty,lte=50"`
	Role     []string `json:"role" binding:"omitempty"`
}
