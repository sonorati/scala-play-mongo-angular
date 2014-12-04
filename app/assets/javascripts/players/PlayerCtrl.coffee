
class PlayerCtrl

    constructor: (@$log, @PlayerService) ->
        @$log.debug "constructing PlayerController"
        @players = []
        @getAllPlayers()

    getAllPlayers: () ->
        @$log.debug "getAllPlayers()"

        @PlayerService.listPlayers()
        .then(
            (data) =>
                @$log.debug "Promise returned #{data.length} Players"
                @players = data
            ,
            (error) =>
                @$log.error "Unable to get Players: #{error}"
            )


controllersModule.controller('PlayerCtrl', PlayerCtrl)
