# WATNI

## Redis run command
```
1. redis image pull    
    - docker pull redis:alpine
2. redis image run command
    - docker run -p 6379:6379 --name {contanier_name} --appendonly yes --requirepass ${password}
3. redis-cli command example
    - redis-cli
    - auth ${password}
    - select 0
```