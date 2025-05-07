# Breaking change



The external user system has been split. Membership-related fields are deprecated and now handled by a new external membership system, also http. 

```
GET {USERS_HOST}/v2/users/{id} -> { "id": "string", "name": "string", "email": "string" }
GET {MEMBERSHIP_HOST}/v2/{user_id}/memberships -> { "id": "string", "name": BASIC|PREMIUM, "features": ["string"] }
```

Where do we start? 
- Changing the current UserHttpClient?
- Introducing the new MembershipHttpClient?
- Changing our service and domain?

Don't code it, just try to calculate how many changes we need to do here, painful right?

As we can see, tech usually changes more often than our business part, and the ripple effect of these changes can be really
intrusive.

Now, let's see how we can mitigate this pain with [hexagonal architecture](/workshop_steps/hexagonal/1_basic_structure.md).