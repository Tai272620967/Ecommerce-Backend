resource "aws_security_group" "bastion" {
  name        = "${local.prefix}-bastion-sg"
  description = "Allow SSH access to Bastion Host"
  vpc_id      = aws_vpc.main.id

  # Cho phép SSH từ máy local của bạn
  ingress {
    protocol    = "tcp"
    from_port   = 22
    to_port     = 22
    cidr_blocks = ["0.0.0.0/0"]  # Đổi thành IP của bạn
  }

  # Cho phép EC2 truy cập RDS
  egress {
    protocol    = "tcp"
    from_port   = 3306
    to_port     = 3306
    # security_groups = [aws_security_group.rds.id]
    cidr_blocks = ["10.1.0.0/16"]
  }

  # Cho phép EC2 truy cập Internet (ping Google, cài package, v.v.)
  egress {
    protocol    = "-1" # Cho phép tất cả các giao thức
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"] # Cho phép tất cả traffic outbound
  }

  tags = {
    Name = "${local.prefix}-bastion-sg"
  }
}

resource "aws_instance" "bastion" {
  ami           = "ami-0474ac020852b87a9" # Amazon Linux 2 (Đổi AMI theo region)
  instance_type = "t3.micro"
  subnet_id     = aws_subnet.public_a.id  # Đảm bảo có public subnet
  key_name      = "muji-db" # Tạo key trên AWS EC2 trước
  vpc_security_group_ids = [aws_security_group.bastion.id]

  associate_public_ip_address = true

  tags = {
    Name = "${local.prefix}-bastion"
  }
}
