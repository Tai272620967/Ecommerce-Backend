output "vpc_id" {
  value       = aws_vpc.main.id
  description = "ID của VPC chính"
}

output "public_subnets" {
  value       = [aws_subnet.public_a.id, aws_subnet.public_b.id]
  description = "Danh sách public subnets"
}

output "private_subnets" {
  value       = [aws_subnet.private_a.id, aws_subnet.private_b.id]
  description = "Danh sách private subnets"
}
