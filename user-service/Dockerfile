# building go binaries
FROM golang:alpine as builder

WORKDIR /go/src/app

RUN go get github.com/cespare/reflex

COPY go.mod .
COPY go.sum .

RUN go mod download

COPY . .

RUN go build -o ./run .


FROM alpine:latest

RUN apk --no-cach add ca-certificates

# create user
RUN mkdir -p /home/userservice
RUN addgroup -S userservice && adduser -S userservice -G userservice

WORKDIR /home/userservice

COPY --chown=userservice:userservice --from=builder /go/src/app/run .

USER userservice

CMD ["./run"]
