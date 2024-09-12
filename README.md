# Perfect Placements  

Project made for Advanced Internet Technologies course. Goal was to create a service, which would allow user to create and edit ranking of their favourite music albums. Other functionality includes ability to register, login and search for music using Spotify API.

Project name references one of my favourite [songs](https://www.youtube.com/watch?v=J0DjcsK_-HY).

## Demo
Here's demo video of this app, available [here](https://www.youtube.com/watch?v=z4pwkf0EIJ8) on YouTube.

[![perfect placements demo](https://img.youtube.com/vi/z4pwkf0EIJ8/0.jpg)](https://www.youtube.com/watch?v=z4pwkf0EIJ8)

## Technologies used
- Java and Spring Boot, Spring Security, Spring Data
- PostreSQL database on ElephantSQL
- JavaScript and React
- Docker
- Postman
- Spotify Web API Java library

## Backend
Backend was made in Java using Spring. Classed are divided into folders based on their functionality

- 📁 **configuration** - configuration files
  - 📄 ``SecurityConfiguration`` - contains security keys, encoders, decoders etc.
- 📁 **controllers** -  managing REST endpoints
  - 📄 ``AuthenticationController`` - register and login
  - 📄 ``RankingController`` - ranking functionality
- 📁 **dtos** -  Data Transfer Objects
  - 📄 ``EntryDTO`` - creating ranking entry
  - 📄 ``EntryDataDTO`` - returning ranking entry (additional image data, position in ranking)
  - 📄 ``LoginResponseDTO``
  - 📄 ``RankingDTO``
  - 📄 ``RegistrationDTO``
- 📁 **model** -  representing database objects
  - 📄 ``ApplicationUser``
  - 📄 ``Entry``
  - 📄 ``Ranking``
  - 📄 ``Role``
- 📁 **repository** -  for Spring Data entities
  - 📄 ``EntryRepository``
  - 📄 ``RankingRepository``
  - 📄 ``RoleRepository``
  - 📄 ``UserRepository``
- 📁 **services** -  application logic
  - 📄 ``AuthenticationService`` - registration and login
  - 📄 ``RankingService`` - ranking functionality
  - 📄 ``SpotifyService`` - methods using Spotify Web API
  - 📄 ``TokenService`` - managing JWT tokens
  - 📄 ``UserService`` - getting users from database
- 📁 **utils** -  helpful additions
  - 📄 ``EntryComparator`` - for comparing ranking entries
  - 📄 ``KeyGeneratorUtility`` - for creating RSA keys
  - 📄 ``RSAKeyProperties``
  
## Frontend
Made with JavaScirpt and React. 
- ``App`` - main class
- ``Login`` - login and register components
- ``MainBody``
- ``Menu`` - component with adding ranking functionality
- ``Search`` - for searching
- ``SearchAlbum`` - component showing album on the search list
- ``Sidebar``
- ``SortableItem`` - ranking item that you can move with mouse
- ``SortableList`` - ranking
- ``utils`` - additional methods

## Endpoints

<table><thead>
  <tr>
    <th>Endpoint</th>
    <th>Type</th>
    <th>Description</th>
  </tr></thead>
<tbody>
  <tr>
    <td colspan="3">AuthenticationController</td>
  </tr>
  <tr>
    <td>/auth/register</td>
    <td>POST</td>
    <td>user register</td>
  </tr>
  <tr>
    <td>/auth/login</td>
    <td>POST</td>
    <td>user login</td>
  </tr>
  <tr>
    <td colspan="3">RankingController</td>
  </tr>
  <tr>
    <td>/ranking/all</td>
    <td>GET</td>
    <td>returns all ranking in database</td>
  </tr>
  <tr>
    <td>/ranking/{id}</td>
    <td>GET</td>
    <td>finds ranking with id</td>
  </tr>
  <tr>
    <td>/ranking</td>
    <td>POST</td>
    <td>add ranking</td>
  </tr>
  <tr>
    <td>/ranking/{id}</td>
    <td>POST</td>
    <td>add position to ranking with id</td>
  </tr>
  <tr>
    <td>/ranking/{id}/sorted</td>
    <td>GET</td>
    <td>get sorted positions from ranking with id</td>
  </tr>
  <tr>
    <td>/ranking/{id}/{entryId}</td>
    <td>GET</td>
    <td>return position from ranking </td>
  </tr>
  <tr>
    <td>/ranking/search/{q}</td>
    <td>GET</td>
    <td>searching Spotify database for query</td>
  </tr>
  <tr>
    <td>/ranking/user</td>
    <td>GET</td>
    <td>returns all user rankings</td>
  </tr>
  <tr>
    <td>/ranking/{id}/{oldpos}/{newpos}</td>
    <td>PUT</td>
    <td>changes position of entry in ranking</td>
  </tr>
  <tr>
    <td>/ranking/{id}</td>
    <td>DELETE</td>
    <td>removes ranking</td>
  </tr>
  <tr>
    <td>/ranking/{id}/{entry}</td>
    <td>DELETE</td>
    <td>removes entry from ranking</td>
  </tr>
</tbody></table>
