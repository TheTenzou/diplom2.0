package interfaces

import (
	"context"

	"github.com/TheTenzou/gis-diplom/user-service/model"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

// UserService defines methods the handler layer expects
type UserService interface {

	// fetch user by id from databse
	// userID is id of requested user
	FindByID(ctx context.Context, userID primitive.ObjectID) (model.User, error)

	// fetch user by login
	// userLogin is login of requested user
	FindByLogin(ctx context.Context, userLogin string) (model.User, error)

	// return page of users
	// page is number of requested page
	// pageSize is size of page
	FindAll(ctx context.Context, page int /*, pageSize int*/) ([]model.User, error)

	// create user record
	// user login shoud be unique
	// return inserted use with id
	Create(ctx context.Context, user model.User) (model.User, error)

	// update user record
	// user login shoud be unique
	Update(ctx context.Context, user model.User) error

	// mark user is deleted
	// return deleted user
	Delete(ctx context.Context, userID primitive.ObjectID) (model.User, error)
}
