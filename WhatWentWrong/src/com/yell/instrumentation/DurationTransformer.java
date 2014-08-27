package com.yell.instrumentation;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;

import com.yell.annotation.Yell;
import com.yell.annotation.YellChar;
import com.yell.annotation.YellCustom;
import com.yell.annotation.YellException;
import com.yell.annotation.YellInt;
import com.yell.annotation.YellIntArray;
import com.yell.annotation.YellRegexPattern;
import com.yell.annotation.YellString;
import com.yell.annotation.YellStringArray;

//this class will be registered with instrumentation agent
public class DurationTransformer implements ClassFileTransformer {
	public byte[] transform(ClassLoader loader, String className,
			Class classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		byte[] byteCode = classfileBuffer;

		try {
			ClassPool classPool = ClassPool.getDefault();
			classPool.insertClassPath(new ClassClassPath(this.getClass()));
			CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(
					classfileBuffer));
			if (containsYellClassAnnotations(ctClass)) {
				CtMethod[] methods = ctClass.getDeclaredMethods();
				for (CtMethod method : methods) {
					updateWithAlert(ctClass, method);
				}
				byteCode = ctClass.toBytecode();
				ctClass.detach();
			}
		} catch (Throwable ex) {
			System.out.println("Exception: " + ex);
			ex.printStackTrace();

		}

		return byteCode;
	}

	private boolean containsYellClassAnnotations(CtClass ctClass) {
		boolean containsYellClassAnnotations = false;
		Object[] availableAnnotations = ctClass.getAvailableAnnotations();
		for (Object annotation : availableAnnotations) {
			if (annotation instanceof Yell) {
				return true;
			}
		}
		return containsYellClassAnnotations;
	}


	private void updateWithAlert(CtClass ctClass, CtMethod method) {
		Object[] availableAnnotations = method.getAvailableAnnotations();
		for (Object annotation : availableAnnotations) {
			if (annotation instanceof YellChar) {
				updateWithCharAlerter(ctClass, method,(YellChar) annotation);
			} else if (annotation instanceof YellCustom){
				updateWithCustomAlerter(ctClass, method,(YellCustom) annotation);
			} else if (annotation instanceof YellInt){
				updateWithIntAlerter(ctClass, method,(YellInt) annotation);
			} else if (annotation instanceof YellIntArray){
				updateWithIntArrayAlerter(ctClass, method,(YellIntArray) annotation);
			} else if (annotation instanceof YellRegexPattern){
				updateWithRegexPatternAlerter(ctClass, method,(YellRegexPattern) annotation);
			}  else if (annotation instanceof YellString){
				updateWithStringAlerter(ctClass, method,(YellString) annotation);
			} else if (annotation instanceof YellStringArray){
				updateWithStringArrayAlerter(ctClass, method,(YellStringArray) annotation);
			} else if (annotation instanceof YellException){
				updateWithExceptionAlerter(ctClass, method,(YellException) annotation);
			}/*else if (annotation instanceof YellThrowsCheckedException){
				updateWithIntAlerter(ctClass, method,(YellThrowsCheckedException) annotation);
			} else if (annotation instanceof YellThrowsUncheckedException){
				updateWithThrowsUncheckedExceptionAlerter(ctClass, method,(YellThrowsUncheckedException) annotation);
			}*/
		}
		
	}

	private void updateWithExceptionAlerter(CtClass ctClass, CtMethod method,
			YellException annotation) {
		String name = method.getName();
	
		makeMethodPrivate(method);
		
		CtMethod newMethod;
		try {
			String returnType = method.getReturnType().getName();
			newMethod = new CtMethod(method.getReturnType(), name, method.getParameterTypes(), ctClass);
			newMethod.setModifiers(AccessFlag.clear(AccessFlag.setPublic(newMethod.getModifiers()), AccessFlag.ABSTRACT));
			
			newMethod.setBody("{ " 
			+  "if (!com.yell.webservice.Service.getInstance().isRun())" 
					+ ("void".equals(returnType) ? method.getName() : ("return " + method.getName())) + "();"
			+ "try {"
			+ ("void".equals(returnType) ? method.getName() : ("return " + method.getName())) + "();"
			+ "} catch (" + annotation.throwable().getCanonicalName() +" ex) {"
			+ "com.yell.webservice.YellMessage yellMessage = new com.yell.webservice.YellMessage(\""+ annotation.message() + " with message:\"  + ex.getMessage()" + " ,\""+ ctClass.getName() +"\",\""+ name +"\");\n"
			+ "com.yell.webservice.Service.getInstance().getMessages().add(yellMessage);\n" 
			+ "throw ex;"
			+ "}"
			+ "}");
			
			ctClass.addMethod(newMethod);
			
		} catch (NotFoundException e) {
			System.out.println(e);
		} catch (CannotCompileException e) {
			System.out.println(e);
		}
		
		
	}

	
	private void updateWithIntAlerter(CtClass cc, CtMethod m, YellInt yellInt) {
		try {
			if (m.getReturnType().getName() == int.class.getName()) {
				updateWithPrimitiveAlerter(cc, m, yellInt.times(), yellInt.message(), "" + yellInt.result(), int.class.getName());
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateWithStringAlerter(CtClass cc, CtMethod m, YellString yellString) {

		try {
			if (m.getReturnType().getName() == char.class.getName()) {
				updateWithPrimitiveAlerter(cc, m, yellString.times(), yellString.message(), yellString.result(), String.class.getName());
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateWithCharAlerter(CtClass cc, CtMethod m, YellChar yellChar) {

		try {
			if (m.getReturnType().getName() == char.class.getName()) {
				updateWithPrimitiveAlerter(cc, m, yellChar.times(), yellChar.message(), "'" + yellChar.result() + "'", char.class.getName());
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateWithPrimitiveAlerter(CtClass cc, CtMethod m, int times, String message, String result, String type) throws CannotCompileException, NotFoundException {

				String name = m.getName();
				makeMethodPrivate(m);

				if(times > 0){
					CtMethod newMethod = new CtMethod(m.getReturnType(), name, m.getParameterTypes(), cc);
					newMethod.setModifiers(AccessFlag.clear(AccessFlag.setPublic(newMethod.getModifiers()), AccessFlag.ABSTRACT));

				if (times == 1) {
					
					newMethod.setBody( " { " + type + " result =  this." + callWrappedMethod(m) 
							+ "if (!com.yell.webservice.Service.getInstance().isRun()) return result;"
							+ "if (result == " + result + ") {"
							+ "System.out.println(\"intercepted result char is: \" + result);\n"
							+ "com.yell.webservice.YellMessage yellMessage = new com.yell.webservice.YellMessage(\""+ message +"\",\""+ cc.getName() +"\",\""+ name +"\");\n"
							+ "com.yell.webservice.Service.getInstance().getMessages().add(yellMessage);\n" 
							+ "} " 
							+ " return result; } ");
						
					} else if (times > 1) {
                        String queueName = "queueCharTimes";
						createQueue(cc, queueName);
				
						newMethod.setBody( " { " + type + " result =  this." + callWrappedMethod(m)
								+ "if (!com.yell.webservice.Service.getInstance().isRun()) return result;"
								+ "if (result == " + result + ") {"
								+ "System.out.println(\"intercepted result char is: \" + result);\n"
								+ "if (queueCharTimes.size() == "+ (times- 1) +") {"
								+ "com.yell.webservice.YellMessage yellMessage = new com.yell.webservice.YellMessage(\""+ message+"\",\""+ cc.getName() +"\",\""+ name +"\");\n"
								+ "com.yell.webservice.Service.getInstance().getMessages().add(yellMessage);\n" 
								+ "for (int i = 0; i < "+(times- 1)+"; i++) {"
								+ "queueCharTimes.poll();"
								+ "}"	
								+ "} else {"
								+ "com.yell.webservice.YellMessage yellMessage = new com.yell.webservice.YellMessage(\""+message+"\",\""+ cc.getName() +"\",\""+ name +"\");\n"
								+ "queueCharTimes.add(yellMessage);\n"
								+ "} }"
								+ " return result; } ");
					}
					cc.addMethod(newMethod);
				}
			
		
	}


	private String callWrappedMethod(CtMethod m) {
		return m.getName() + "($$);";
	}

	private void updateWithIntArrayAlerter(CtClass ctClass, CtMethod method, YellIntArray annotation) {
		try {
			if (method.getReturnType().getName() == int.class.getName()) {

				String resultListF = Arrays.toString(annotation.result());
				String result = resultListF.substring(1, resultListF.length() - 1);
				updateWithArrayAlerter(ctClass, method, annotation.message(), result, int.class.getName());
			}
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateWithStringArrayAlerter(CtClass ctClass, CtMethod method, YellStringArray annotation) {
		System.out.println(" updateWithStringArrayAlerter -->");
		try {
			if (method.getReturnType().getName().equals(String.class.getName())) {
				
				String result = new String();
				for (int i = 0; i < annotation.result().length; i++){
					result += "\""+annotation.result()[i]+"\", ";
				}
				result = result.substring(0, result.length() - 2);
				System.out.println(result);
				updateWithArrayAlerter(ctClass, method, annotation.message(), result, String.class.getName());
			}
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateWithArrayAlerter(CtClass ctClass, CtMethod method, String message, String result, String type) throws CannotCompileException, NotFoundException {
		String name = method.getName();
		makeMethodPrivate(method);

		CtMethod newMethod = new CtMethod(method.getReturnType(), name, method.getParameterTypes(), ctClass);
		newMethod.setModifiers(AccessFlag.clear(AccessFlag.setPublic(newMethod.getModifiers()), AccessFlag.ABSTRACT));

		newMethod.setBody( " { "+type+" result =  this." + callWrappedMethod(method)
				+ "if (!com.yell.webservice.Service.getInstance().isRun()) return result;"
				+ type+"[] resultListAnnotation = new "+type+"[]{" +result+"};"
				+ "boolean found = false;"
				+ "for (int i = 0; i< resultListAnnotation.length ; i++) {\n"
				+ "found |= resultListAnnotation[i] == result;}"
				+ "if (found) {"
				+ "System.out.println(\"intercepted result  is: \" + result);\n"
				+ "com.yell.webservice.YellMessage yellMessage = new com.yell.webservice.YellMessage(\""+ message +"\",\""+ ctClass.getName() +"\",\""+ name +"\");\n"
				+ "com.yell.webservice.Service.getInstance().getMessages().add(yellMessage);\n" 
				+ "} " 
				+ " return result; } ");
		ctClass.addMethod(newMethod);		
	}
	

	private void updateWithRegexPatternAlerter(CtClass ctClass, CtMethod method, YellRegexPattern annotation) {
		try{
			String name = method.getName();
			makeMethodPrivate(method);

			CtMethod newMethod = new CtMethod(method.getReturnType(), name, method.getParameterTypes(), ctClass);
			newMethod.setModifiers(AccessFlag.clear(AccessFlag.setPublic(newMethod.getModifiers()), AccessFlag.ABSTRACT));

			newMethod.setBody( " { "+ method.getReturnType().getName()+ " result =  this." + callWrappedMethod(method)
					+ "if (!com.yell.webservice.Service.getInstance().isRun()) return result;"
					+ "if (result.matches(\""+annotation.regexPattern()+"\")) {"
					+ "System.out.println(\"intercepted result char is: \" + result);\n"
					+ "com.yell.webservice.YellMessage yellMessage = new com.yell.webservice.YellMessage(\""+ annotation.message() +"\",\""+ ctClass.getName() +"\",\""+ name +"\");\n"
					+ "com.yell.webservice.Service.getInstance().getMessages().add(yellMessage);\n" 
					+ "} " 
					+ " return result; } ");
			ctClass.addMethod(newMethod);
		
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateWithCustomAlerter(CtClass ctClass, CtMethod method, YellCustom annotation) {
			
		CtClass returnTypeClass = null;
		try {
			returnTypeClass = method.getReturnType();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		if (returnTypeClass != null) {
			if (!annotation.element().isEmpty()) {
				String[] path = annotation.element().split("\\.");

				boolean allAtribFound = true;
				for (String variable : path) {
					CtField ctField = null;
					try {
						ctField = returnTypeClass.getDeclaredField(variable);
					} catch (NotFoundException e) {
						e.printStackTrace();
						allAtribFound = false;
					}

					if (ctField != null) {
						try {
							returnTypeClass = ctField.getType();
						} catch (NotFoundException e) {
							e.printStackTrace();
							allAtribFound = false;
						}
					}
				}
				
				if (allAtribFound){
					String name = method.getName();
					makeMethodPrivate(method);
					try {
					CtMethod newMethod = new CtMethod(method.getReturnType(), name, method.getParameterTypes(), ctClass);
					newMethod.setModifiers(AccessFlag.clear(AccessFlag.setPublic(newMethod.getModifiers()), AccessFlag.ABSTRACT));

					newMethod.setBody( 
							" { "+ method.getReturnType().getName()+ " result =  this." + callWrappedMethod(method)
							+ "if (!com.yell.webservice.Service.getInstance().isRun()) return result;"
							+ "String[] path = \""+annotation.element()+"\".split(\"\\\\.\");"
							+ "Class clazz = "+method.getReturnType().getName()+".class;"
							+ "Object value = result;"
							+ "boolean finishedCorrectly=true;"
							+ "try{ for (int i = 0; i < path.length; i++) {"
							+ "  String var = path[i];"
							+ "  java.lang.reflect.Field field = clazz.getDeclaredField(var);"
							+ "  field.setAccessible(true);"
							+ "  value = field.get(value);"
							+ "  clazz = field.getType(); }"
							+ "} catch (Exception e) { e.printStackTrace(); finishedCorrectly = false;}"
							+ "if (finishedCorrectly && value.equals(\""+annotation.result()+"\")){"
							+ "com.yell.webservice.YellMessage yellMessage = new com.yell.webservice.YellMessage(\""+ annotation.message() +"\",\""+ ctClass.getName() +"\",\""+ name +"\");\n"
					        + "com.yell.webservice.Service.getInstance().getMessages().add(yellMessage);\n" 
							+ "}"
							+ " return result; } ");
							
						ctClass.addMethod(newMethod);
						} catch (CannotCompileException | NotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			}
		}
	}
	
	private void makeMethodPrivate(CtMethod m) {
		if (AccessFlag.isPublic(m.getModifiers())) {
			m.setName(m.getName() + "Wrapped");
			m.setModifiers(AccessFlag.PRIVATE);
		}
		
	}
	
	private void createQueue(CtClass cc, String name) {
		try {
			cc.getDeclaredField(name);
		} catch (NotFoundException e) {
			try {
				CtClass arrListClazz = ClassPool.getDefault().get("java.util.Queue");
				CtField f = new CtField(arrListClazz, name, cc);

				f.setModifiers(Modifier.STATIC | Modifier.PRIVATE);
				cc.addField(f, CtField.Initializer.byNew(ClassPool.getDefault().get("java.util.LinkedList")));
			} catch (CannotCompileException e1) {
				System.out.println("Can not create Queue " + name);
				e1.printStackTrace();
			} catch (NotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
