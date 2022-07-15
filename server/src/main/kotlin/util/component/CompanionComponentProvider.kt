package argoner.server.util.component

interface CompanionComponentProvider {

    operator fun invoke(container: ComponentContainer<*>)

}