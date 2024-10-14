# Main repo
[![Rank System](https://img.shields.io/badge/Rank_System-eba0ac?style=for-the-badge&logo=github&logoColor=white&labelColor=1e1e2e)](https://github.com/AndyLocks/RankSystem)
# All microservices
[![Bot](https://img.shields.io/badge/Bot-cba6f7?style=for-the-badge&logo=github&logoColor=cdd6f4&labelColor=1e1e2e)](https://github.com/AndyLocks/rank_system.bot)
[![Points Service](https://img.shields.io/badge/Points_service-fab387?style=for-the-badge&logo=github&logoColor=cdd6f4&labelColor=1e1e2e)](https://github.com/AndyLocks/rank_system.points_service)
[![Rewards service](https://img.shields.io/badge/Rewards_service-a6e3a1?style=for-the-badge&logo=github&logoColor=cdd6f4&labelColor=1e1e2e)](https://github.com/AndyLocks/rank_system.rewards_service)
[![Manager](https://img.shields.io/badge/Manager-f38ba8?style=for-the-badge&logo=github&logoColor=cdd6f4&labelColor=1e1e2e)](https://github.com/AndyLocks/rank_system.manager)

---

**This project is part of [another project.](https://github.com/AndyLocks/RankSystem)**
# Rank system bot
## Description
It's a bot microservice. It is the main way of communication with the application. It also counts points, gives rewards and sends notifications.
## Commands

`/goals` - Show a list of awards

`/delete_reward_goal` - Delete a reward goal by id
> `id` - reward goal id

`/new_role_goal` - Create a new award
> `role` - a discord role
> 
> `points` - points for which you can get a role

`\rank` - Show a list of points

`\new_url_goal` - Create a new award
> `url` - Link as a reward. It can be a YouTube video accessible only by link or an invitation to a secret discord server.
> 
> `points` - points for which you can get a link

---

![bot.png](./bot.png)
