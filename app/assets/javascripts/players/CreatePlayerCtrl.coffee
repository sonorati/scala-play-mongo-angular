
class CreatePlayerCtrl

    constructor: (@$log, @$location,  @PlayerService) ->
        @$log.debug "constructing CreatePlayerController"
        @Player = {}

    createPlayer: () ->
        @$log.debug "createPlayer()"
        @player.active = true
        @PlayerService.createPlayer(@player)
        .then(
            (data) =>
                @$log.debug "Promise returned #{data} Player"
                @player = data
                @$location.path("/")
            ,
            (error) =>
                @$log.error "Unable to create Player: #{error}"
            )

controllersModule.controller('CreatePlayerCtrl', CreatePlayerCtrl)
