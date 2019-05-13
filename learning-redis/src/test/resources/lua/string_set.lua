-- string_set.lua

local exist = redis.call('EXISTS', KEYS[1]);

if exist then
    return "key exist..";
end

exist = redis.call('SET', KEYS[1], ARGV[1]) > 0;
if exist then
    return "key set success..";
else
    return "key set fail..";
end
