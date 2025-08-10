package poggers.oldlegion

import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

/**
 * Originally from KoL - Couldn't succeed in getting it working with non-Kotlin ReflectionUtils
 */
object ReflectionUtilities {

    private val fieldClass = Class.forName("java.lang.reflect.Field", false, Class::class.java.classLoader)
    private val setFieldHandle = MethodHandles.lookup().findVirtual(fieldClass, "set", MethodType.methodType(Void.TYPE, Any::class.java, Any::class.java))
    private val getFieldHandle = MethodHandles.lookup().findVirtual(fieldClass, "get", MethodType.methodType(Any::class.java, Any::class.java))
    private val getFieldTypeHandle = MethodHandles.lookup().findVirtual(fieldClass, "getType", MethodType.methodType(Class::class.java))
    private val getFieldNameHandle = MethodHandles.lookup().findVirtual(fieldClass, "getName", MethodType.methodType(String::class.java))
    private val setFieldAccessibleHandle = MethodHandles.lookup().findVirtual(fieldClass,"setAccessible", MethodType.methodType(Void.TYPE, Boolean::class.javaPrimitiveType))

    private val methodClass = Class.forName("java.lang.reflect.Method", false, Class::class.java.classLoader)
    private val getMethodNameHandle = MethodHandles.lookup().findVirtual(methodClass, "getName", MethodType.methodType(String::class.java))
    private val invokeMethodHandle = MethodHandles.lookup().findVirtual(methodClass, "invoke", MethodType.methodType(Any::class.java, Any::class.java, Array<Any>::class.java))

    internal val getMethodReturnHandle = MethodHandles.lookup().findVirtual(methodClass, "getReturnType", MethodType.methodType(Class::class.java))
    private val getMethodParametersHandle = MethodHandles.lookup().findVirtual(methodClass, "getParameterTypes", MethodType.methodType(arrayOf<Class<*>>().javaClass))

    @JvmStatic
    fun set(fieldName: String, instanceToModify: Any, newValue: Any?) {
        var field: Any? = null

        try {
            field = instanceToModify.javaClass.getField(fieldName)
        } catch (_: Exception) {
        }
        if (field == null) {
            field = instanceToModify.javaClass.getDeclaredField(fieldName)
        }

        setFieldAccessibleHandle.invoke(field, true)
        setFieldHandle.invoke(field, instanceToModify, newValue)
    }

    @JvmStatic
    fun get(fieldName: String, instanceToGetFrom: Any): Any? {
        var field: Any? = null

        try {
            field = instanceToGetFrom.javaClass.getField(fieldName)
        } catch (_: Exception) {
        }
        if (field == null) {
            field = instanceToGetFrom.javaClass.getDeclaredField(fieldName)
        }

        setFieldAccessibleHandle.invoke(field, true)
        return getFieldHandle.invoke(field, instanceToGetFrom)
    }

    fun invoke(methodName: String, instance: Any, vararg arguments: Any?, declared: Boolean = false): Any? {
        val method: Any?

        val clazz = instance.javaClass
        val args = arguments.map { it!!::class.javaPrimitiveType ?: it::class.java }
        val methodType = MethodType.methodType(Void.TYPE, args)

        method = if (!declared) {
            clazz.getMethod(methodName, *methodType.parameterArray()) as Any?
        } else {
            clazz.getDeclaredMethod(methodName, *methodType.parameterArray()) as Any?
        }

        return invokeMethodHandle.invoke(method, instance, arguments)
    }

}