variable "region" {
  description = "The AWS region to deploy to"
  type        = string
  default     = "us-east-1"
}

variable "instance_type_app" {
  description = "EC2 instance type for the Spring Boot application"
  type        = string
  default     = "t2.micro"
}

variable "instance_type_db" {
  description = "EC2 instance type for the Book DB"
  type        = string
  default     = "t3.medium"
}

variable "db_user" {
  description = "Username for the MySQL database"
  type        = string
  default   = "appuser"
}

variable "db_password" {
  description = "Password for the MySQL database"
  type        = string
  sensitive   = true
}

variable "db_name" {
  description = "Name of the MySQL database"
  type        = string
  default     = "mitroo"
}

variable "vpc_id" {
  description = "VPC ID for the infrastructure"
  type        = string
  default     = "vpc-0a243927b45d137a6"
}

variable "subnets" {
  description = "Subnets for the load balancer"
  type        = list(string)
  default     = ["subnet-02c3e02ebec7767cd", "subnet-00e3833eb735809c0"]
}

variable "key_name" {
  description = "The name of the SSH key pair"
  type        = string
  default     = "friend1"
}

variable "jar_name" {
  description = "The name of the app jar file"
  type        = string
  default     = "mitroo-spring-0.1"
}