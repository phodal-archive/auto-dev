import org.junit.Ignore
    @Test
    fun testModifyImportChange() {
        val code = """
            Index: server/src/main/kotlin/com/thoughtworks/archguard/code/module/infrastructure/dubbo/DubboConfigRepositoryImpl.kt
            --- a/server/src/main/kotlin/com/thoughtworks/archguard/code/module/infrastructure/dubbo/DubboConfigRepositoryImpl.kt	
            +++ b/server/src/main/kotlin/com/thoughtworks/archguard/code/module/infrastructure/dubbo/DubboConfigRepositoryImpl.kt	(date 1704766567000)
            @@ -2,7 +2,7 @@
             
             import com.thoughtworks.archguard.code.module.domain.dubbo.DubboConfigRepository
             import org.archguard.protocol.dubbo.ReferenceConfig
            -import com.thoughtworks.archguard.code.module.domain.dubbo.ServiceConfig
            +import org.archguard.protocol.dubbo.ServiceConfig
             import org.archguard.protocol.dubbo.SubModuleDubbo
             import org.assertj.core.api.Assertions.assertThat
             import org.junit.jupiter.api.Test
        """.trimIndent()

        val postProcess = DiffSimplifier.postProcess(code)
        assertEquals(
            postProcess,
            """modify file server/src/main/kotlin/com/thoughtworks/archguard/code/module/infrastructure/dubbo/DubboConfigRepositoryImpl.kt
change import from com.thoughtworks.archguard.code.module.domain.dubbo.ServiceConfig to org.archguard.protocol.dubbo.ServiceConfig"""
        )
    }

    @Test
    fun handleForRenameAndChangeImport() {
        val code = """
            Index: server/src/main/kotlin/com/thoughtworks/archguard/code/module/domain/model/LeafManger.kt
            rename from server/src/main/kotlin/com/thoughtworks/archguard/code/module/domain/model/LeafManger.kt
            rename to server/metric-service/src/main/kotlin/org/archguard/arch/LeafManger.kt
            @@ -2,7 +2,7 @@
             
             import com.thoughtworks.archguard.code.module.domain.dubbo.DubboConfigRepository
             import org.archguard.protocol.dubbo.ReferenceConfig
            -import com.thoughtworks.archguard.code.module.domain.dubbo.ServiceConfig
            +import org.archguard.protocol.dubbo.ServiceConfig
             import org.archguard.protocol.dubbo.SubModuleDubbo
             import org.assertj.core.api.Assertions.assertThat
             import org.junit.jupiter.api.Test
        """.trimIndent()

        val postProcess = DiffSimplifier.postProcess(code)
        assertEquals(
            postProcess,
            """rename file server/src/main/kotlin/com/thoughtworks/archguard/code/module/domain/model/LeafManger.kt server/metric-service/src/main/kotlin/org/archguard/arch/LeafManger.kt
change import from com.thoughtworks.archguard.code.module.domain.dubbo.ServiceConfig to org.archguard.protocol.dubbo.ServiceConfig"""
        )
    }

    @Test
    fun testHandleForFileChange() {
        val code = """--- a/server/src/test/kotlin/com/thoughtworks/archguard/code/clazz/domain/CodeTreeTest.kt
+++ b/server/src/test/kotlin/com/thoughtworks/archguard/code/clazz/domain/CodeTreeTest.kt	(date 1704769088000)
@@ -1,4 +1,4 @@"""

        val postProcess = DiffSimplifier.postProcess(code)
        assertEquals(
            postProcess,
            """modify file server/src/test/kotlin/com/thoughtworks/archguard/code/clazz/domain/CodeTreeTest.kt"""
        )
    }
}