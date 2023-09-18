//package com.samodule.util;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.hibernate.transform.ResultTransformer;
//
//public class DTOResultTransformer
//
//		implements ResultTransformer {
//
//	private Map<Long, PostDTO> postDTOMap = new LinkedHashMap<>();
//
//	@Override
//	public Object transformTuple(Object[] tuple, String[] aliases) {
//
//		Map<String, Integer> aliasToIndexMap = aliasToIndexMap(aliases);
//
//		Long postId = longValue(tuple[aliasToIndexMap.get(PostDTO.ID_ALIAS)]);
//
//		PostDTO postDTO = postDTOMap.computeIfAbsent(postId, id -> new PostDTO(tuple, aliasToIndexMap));
//
//		postDTO.getComments().add(new PostCommentDTO(tuple, aliasToIndexMap));
//
//		return postDTO;
//	}
//
//	@Override
//	public List transformList(List collection) {
//		return new ArrayList<>(postDTOMap.values());
//	}
//
//	public Map<String, Integer> aliasToIndexMap(String[] aliases) {
//
//		Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();
//
//		for (int i = 0; i < aliases.length; i++) {
//			aliasToIndexMap.put(aliases[i], i);
//		}
//
//		return aliasToIndexMap;
//	}
//
//}
