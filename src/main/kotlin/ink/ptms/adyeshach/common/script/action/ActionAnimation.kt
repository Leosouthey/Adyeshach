package ink.ptms.adyeshach.common.script.action

import com.google.common.base.Enums
import ink.ptms.adyeshach.common.bukkit.BukkitAnimation
import ink.ptms.adyeshach.common.script.ScriptHandler.entitySelected
import ink.ptms.adyeshach.common.script.ScriptHandler.getEntities
import ink.ptms.adyeshach.common.script.ScriptHandler.getManager
import ink.ptms.adyeshach.common.script.ScriptHandler.loadError
import taboolib.module.kether.*
import java.util.concurrent.CompletableFuture

/**
 * @author IzzelAliz
 */
class ActionAnimation(val animation: BukkitAnimation): ScriptAction<Void>() {

    override fun run(frame: ScriptFrame): CompletableFuture<Void> {
        val s = frame.script()
        if (s.getManager() == null) {
            error("No manager selected.")
        }
        if (!s.entitySelected()) {
            error("No entity selected.")
        }
        s.getEntities()!!.filterNotNull().forEach {
            it.displayAnimation(animation)
        }
        return CompletableFuture.completedFuture(null)
    }

    companion object {

        @KetherParser(["animation"], namespace = "adyeshach", shared = true)
        fun parser() = scriptParser {
            val type = it.nextToken()
            val animation = Enums.getIfPresent(BukkitAnimation::class.java, type.uppercase()).orNull() ?: throw loadError("Unknown animation $type")
            ActionAnimation(animation)
        }
    }
}