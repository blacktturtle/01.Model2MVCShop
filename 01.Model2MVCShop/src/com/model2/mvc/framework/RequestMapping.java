package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	private RequestMapping(String resources) {
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);
			properties = new Properties(); // Properties  => String만 저장하는 Map구조('=' 을 기준으로 key, value로 나누어서 저장)
			properties.load(in); // properties에 모든 내용 저장 (key, value로)
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	public Action getAction(String path){
		Action action = map.get(path); //path : logon.do
		if(action == null){
			String className = properties.getProperty(path); // getProperty(key), return : String 키값을 넣으면 속성값 찾음 아무것도 없으면 null리턴
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim(); // trim : 선행 및 후행 공백을 제거
			try{
				Class c = Class.forName(className); //해당 이름을 갖는 클래스의 정보를 담은 Class instance를 리턴
				//System.out.println("c는 어떻게 생성되나 : " + c);
				Object obj = c.newInstance(); // Object obj = new LoginAction()과 같음 (이 클래스 개체로 표시된 클래스의 새 인스턴스를 생성합니다)
				//System.out.println("obj는 무엇? " + obj.getClass().getName());
				if(obj instanceof Action){
					map.put(path, (Action)obj); // (/logon.do , com.model2.mvc.view.user.LoginAction);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class형변환시 오류 발생 ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action; //com.model2.mvc.view.user.LoginAction 리턴
	}
}