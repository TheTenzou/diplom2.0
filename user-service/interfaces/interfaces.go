package interfaces

import (
	"context"

	"github.com/TheTenzou/diplom2.0/user-service/model"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type UserRepository interface {
	FindByID(ctx context.Context, userID primitive.ObjectID) (model.User, error)
	FindByLogin(ctx context.Context, userLogin string) (model.User, error)
	FindAll(ctx context.Context, page int) ([]model.User, error)
	Create(ctx context.Context, user model.User) (model.User, error)
	Update(ctx context.Context, user model.User) error
	Delete(ctx context.Context, userID primitive.ObjectID) (model.User, error)
}
