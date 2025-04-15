local key = KEYS[1];

local value = redis.call('get', key);

if (value ~= nil or tonumber(value) < 3) then
    redis.call('incrby', key, 1);
    return 1;
end

return nil;