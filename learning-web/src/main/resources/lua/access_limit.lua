---
--- Description access limit lua
--- Created by yangpengbing.
--- DateTime: 2019-06-13 15:22
---
-- 限流的key
local key = KEYS[1]
-- 限流大小
local limit_size = tonumber(ARGV[1])
local expire = tonumber(ARGV[2])

-- 当前大小
local current_size = tonumber(redis.call('get', key) or "0")

if current_size >= limit_size then
    return 0;
else
    redis.call('incrby', key, 1);
    redis.call('pexpire', key, expire);
    return current_size + 1;
end
