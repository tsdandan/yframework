package yang.framework.validation.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yang.framework.util.StringUtil;

/**
 * 数字のパラメータのバリデーション
 * @author VV000584
 *
 */
public class PatternValidator extends AbstractFormValidator {

	private String errorMessage ="";

	private Pattern[] patterns;

    private String unmatch;

    public PatternValidator(String messagekey, Pattern... patterns) {
        this.patterns = patterns;
        this.errorMessage = messagekey;
    }

    /**
     * バリデーションを実行する
     */
	@Override
	public boolean validate(Object value) {
		StringBuilder sb = new StringBuilder();
        String[] values = getStringArray(value);
        for (String str : values) {
            sb.append(getExceptCharacters(str, patterns));
        }
        unmatch = sb.toString();
        return unmatch.length() == 0;
	}

    /**
     * 文字種チェックメソッド。
     *
     */
    protected String getExceptCharacters(String target, Pattern... patterns) {

        if (StringUtil.isEmpty(target) || patterns == null) {
            return "";
        }
        for (Pattern pattern : patterns) {
            String result = getUnmatchString(target, pattern);
            if ((result.length()!=0)){
            	return result;
            }
        }

        return "";
    }

    /**
     * 指定された文字列のうち、指定した文字種以外の文字を返します。
     * @param checkString
     * @param pattern
     * @return
     */
    public static String getUnmatchString(String checkString, Pattern pattern) {
        Matcher matcher = pattern.matcher(checkString);
        String unmatchsString = matcher.replaceAll("");
        return unmatchsString;
    }

	@Override
	public String getMessage() {
		return errorMessage;
	}

}
