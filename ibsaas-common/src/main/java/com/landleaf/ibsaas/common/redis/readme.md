redis配置说明：

spring:
  redis:
    enable: true  #开启redis配置
    database: 0
    sentinel:  #哨兵模式
      master: mymaster
      nodes: 40.73.87.245:26380,40.73.87.245:26379,40.73.87.245:26381
    pool:
      max-idle: -1
      max-wait: 3000
      max-active: -1
      min-idle: 10
#    host: 40.73.87.245
#    port: 6380
    password: aA123456
