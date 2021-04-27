package requests

// structure for holding and validation incoming login user request
// input argument of bindData
type LoginRequest struct {
	Login    string `json:"login" binding:"required,gte=6,lte=32"`
	Password string `json:"password" binding:"required,gte=6,lte=30"`
}
