package com.synox.test.memo;

import org.springframework.transaction.annotation.Transactional;

public class Memo {

//    public <T> T withFeature(User actor, FeatureType feature, NoArgFunction<T> f) {
//        if (actor.getDeleted() || actor.getBanned())
//            throw new UnauthorizedException("user [" + actor.getLogin() + "] is deleted/banned");
//
//        if (actor.isAdmin() || actor.hasFeature(feature))
//            return f.apply();
//
//        throw new ForbiddenException("user [" + actor.getLogin() + "] misses required feature [" + feature + "]");
//    }

//    @Transactional
//    public void backofficeBan(User actor, Long userId) {
//        withFeature(actor, FeatureType.BO_USERS_BAN, () -> {
//            var user = find(actor, userId)
//                .orElseThrow(() -> new BadRequestException("user [" + userId + "] not found"));
//            user = update(actor, user.withBanned());
//            eventService.fire(new EventAccountBanned(mapperService.toDto(user), ServiceProvider.UNKNOWN.getName()));
//            return user;
//        });
//    }

}
