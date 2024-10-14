[![gpl](https://img.shields.io/badge/gpl-fab387?style=for-the-badge&label=license&labelColor=black)](https://github.com/AndyLocks/LegoTehSet/blob/master/LICENSE)
[![discord](https://img.shields.io/badge/discord-89b4fa?style=for-the-badge&logo=discord&logoColor=white&labelColor=black)]()
[![JDA](https://img.shields.io/badge/JDA-cba6f7?style=for-the-badge&logo=discord&logoColor=white&labelColor=black)](https://github.com/discord-jda/JDA)

[![invite](https://img.shields.io/badge/Bot-f9e2af?style=for-the-badge&logoColor=white&label=Invite&labelColor=black)](https://discord.com/oauth2/authorize?client_id=1114974604784578590)

# All microservices
[![Bot](https://img.shields.io/badge/Bot-cba6f7?style=for-the-badge&logo=github&logoColor=white&labelColor=black)](https://github.com/AndyLocks/rank_system.bot)
[![Points Service](https://img.shields.io/badge/Points_service-fab387?style=for-the-badge&logo=github&logoColor=white&labelColor=black)](https://github.com/AndyLocks/rank_system.points_service)
[![Rewards service](https://img.shields.io/badge/Rewards_service-a6e3a1?style=for-the-badge&logo=github&logoColor=white&labelColor=black)](https://github.com/AndyLocks/rank_system.rewards_service)
[![Manager](https://img.shields.io/badge/Manager-f38ba8?style=for-the-badge&logo=github&logoColor=white&labelColor=black)](https://github.com/AndyLocks/rank_system.manager)

# Rank System
## Description
It's a ranking system for discord servers. It counts points and gives out rewards for messages. The rewards are easily customisable and manageable.
## Rewards
The reward can be a role in discord or a link to other services.

The link could be to a secret youtube video accessible only through a link or a link to a one-time promo code on some service.
It can also be a link to the Rank system API for a cooler reward.

## Structure
![Rank system](./Rank_system.png)

---

### [Bot](https://github.com/AndyLocks/rank_system.bot)
It's a bot microservice. It is the main way of communication with the application. It also counts points, gives rewards and sends notifications to a member.

### [Points service](https://github.com/AndyLocks/rank_system.points_service)
Processes all the points that the bot sends.

### [Rewards service](https://github.com/AndyLocks/rank_system.rewards_service)
Processes the points and decides what awards will be given out. Also sends a notification to the bot.

### [Manager](https://github.com/AndyLocks/rank_system.manager)
This is the main api for managing the service.

### Kafka

#### Notifications
These are all the notifications to be sent by the bot. Users receive notifications directly into a private chat with the bot.

#### Points
Points earned by users that need to be processed.

#### Points for rewards service
This topic is for points consumption by `rewards service`.

`points service` puts the same points into the `points_for_rewards` topic after successful processing.

#### Rewards
All rewards to be given to users.

## Commands
`/goals` - Show a list of awards

![Goals command](./Goals_command.png)

---

`/delete_reward_goal` - Delete a reward goal by id
> `id` - reward goal id

![Delete reward goal](./Delete_reward_goal.png)

---

`/new_role_goal` - Create a new award

> `role` - a discord role
> 
> `points` - points for which you can get a role

![New role reward goal](./New_role_goal.png)

---

`\rank` - Show a list of points

![Rank](./Rank_command.png)

---

`\new_url_goal` - Create a new award
> `url` - Link as a reward. It can be a YouTube video accessible only by link or an invitation to a secret discord server.
> 
> `points` - points for which you can get a link

![New url reward goal](./New_url_reward_goal.png)

## Docker compose etaps
![Docker compose etaps](./Docker_compose_etaps.png)

---

## Invite the bot
[Invite](https://discord.com/oauth2/authorize?client_id=1114974604784578590)

## Build and run
### Clone this repository
```shell
git clone https://github/AndyLocks/RankSystem
```

### Configuration
1. Rename `./bot/.env.example` to `./bot/.env`
2. Write a discord bot token in the `TOKEN` column

   Example: `TOKEN=asSkDSfjwDebt.AbobAkdkjdOnbwdslkfjwelkhfgnkoAJSsdDasdWdHKPjsdkfhn.LKDSJlksdflkjDFlksdjf`

### Install docker and docker compose
Here you can read how to install [Docker compose](https://docs.docker.com/compose/install/).

Here you can read how to install [Docker engine](https://docs.docker.com/engine/install/).

### Run project
```bash
docker compose up --build
```
