package yang.framework.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * クラスに関する処理のユティリティ
 * @author VV000584
 *
 */
public class ClassUtil {
    /**
     * {@link Class}を返します。
     *
     * @param className
     * @return {@link Class}
     * @throws ClassNotFoundException
     *             {@link ClassNotFoundException}がおきた場合
     * @see Class#forName(String)
     */
    public static Class<?> forName(String className)
            throws ClassNotFoundException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            return Class.forName(className, true, loader);
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(className, ex);
        }
    }

    /**
     * クラスのインスタンスを作る
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Object newInstance(Class<?> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
			e.printStackTrace();
        } catch (IllegalAccessException e) {
			e.printStackTrace();
        }
        return null;
    }

    /**
     * destのインスタンスを作って、ｓｒｃの内容をコピー
     * @param clazz
     * @param src
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Object CreateAndCopy(Class<?> clazz, Object src){
    	Object dest = newInstance(clazz);
    	Map<String, Object> values = new HashMap<String, Object>();
		try {
			for (Field field : src.getClass().getFields()){
				String name = field.getName();
				values.put(name, field.get(src));
			}
			for (Field field : clazz.getFields()){
				if (values.containsKey(field.getName())){
					field.set(dest, values.get(field.getName()));
				}
			}
		} catch (SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	return dest;
    }
}
