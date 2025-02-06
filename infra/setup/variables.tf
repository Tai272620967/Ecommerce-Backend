variable "tf_state_bucket" {
  description = "Name of S3 bucket in AWS for storing TF state"
  default     = "muji-app-tf-state"
}

variable "tf_state_lock_table" {
  description = "Name of the DynamoDB table for TF state locking"
  default     = "muji-app-tf-lock"
}

variable "project" {
  description = "Project name for tagging resources"
  default     = "muji-app-api"
}

variable "contact" {
  description = "Contact name for tagging resources"
  default     = "tai272620967@gmail.com"
}
