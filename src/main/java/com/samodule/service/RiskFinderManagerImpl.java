package com.samodule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.samodule.vo.Confilcted;

@Service("riskFinderManager")
public class RiskFinderManagerImpl implements RiskFinderManager {
	Logger log = LoggerFactory.getLogger(getClass());
	int count;
    @Override
	public List<Confilcted> riskFind(Map<String, List<String>> groupByData, Map<String, Set<String>> riskFunctionData) {
		List<Confilcted> confilcteds = new ArrayList<>();
		count = 0;
		for (Entry<String, List<String>> entry : groupByData.entrySet()) {
			String key = entry.getKey();
			log.info("USER/TCODE  :: " + key);
			List<String> functions = entry.getValue();
			Map<String, Set<String>> temp = riskFunctionData.entrySet().stream()
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

			temp.entrySet().forEach(e -> {
				//System.out.println(e.getKey());
				log.info("Risk  :: " + e.getKey());
				Set<String> values = e.getValue();
				//values.forEach(System.out::println);
				// if (functions.size()<2);
				if (functions.containsAll(values)) {
					count = count + 1;
					String riskCode=e.getKey();
					log.info("================================count  +" +count);
					values.forEach(v -> {
						//functions.remove(v);
						confilcteds.add(new Confilcted(key,v, riskCode, count));
						log.info("Function  :: " +v);
					});
					log.info("================================ +");
				}
			});

		}
		// log.info(" RsikFinderEngine >> riskFind >> confilcteds size
		// "+confilcteds.size());
		return confilcteds;
	}
    @Override
	public List<Confilcted> getConflicte(Map<String, Set<String>> temp) {
		List<String> keyList = new ArrayList<String>(temp.keySet());
		List<Confilcted> confilcteds = new ArrayList<>();
		for (int i = 0; i < keyList.size(); i++) {
			Set<String> riski = temp.get(keyList.get(i));
			for (int j = i + 1; j < keyList.size(); j++) {
				Set<String> tempSet = riski.stream().collect(Collectors.toSet());
				Set<String> riskj = temp.get(keyList.get(j));
				// System.out.println("Function i :: " + keyList.get(i));
				// System.out.println("Function j :: " + keyList.get(j));
				// log.info("Function i :: " + keyList.get(i));
				// log.info("Function j :: " + keyList.get(j));
				// System.out.println(temp.get(keyList.get(i)) + " #####temp###### " +
				// temp.get(keyList.get(j)));
				// System.out.println(tempSet + " ########### " + riskj);
				// log.info(tempSet + " ########### " + riskj);

				if (tempSet.retainAll(riskj) && tempSet.size() >= 1) {

					count = count + 1;
					// System.err.println(tempSet.size() + " &&&&&&&&&&&& " + tempSet.toString() + "
					// count " + count);
					log.info("================================ +");
					log.info("Function i :: " + keyList.get(i));
					log.info("Function j :: " + keyList.get(j));
					final int t = i;
					final int x = j;
					tempSet.stream().forEach(u -> {
						confilcteds.add(new Confilcted(keyList.get(t), u, count));
						confilcteds.add(new Confilcted(keyList.get(x), u, count));
					});

					log.info(tempSet.size() + " &&&&&&&&&&&&    " + tempSet.toString() + " count " + count);
					log.info("================================ *");
				}
			}
		}
		// log.info(" RsikFinderEngine >> getMatchRisk >> confilcteds size
		// "+confilcteds.size());
		return confilcteds;
	}
    
    @Override
	public List<Confilcted> riskFindForFunction( Set<String> functions, Map<String, Set<String>> riskFunctionData) {
		List<Confilcted> confilcteds = new ArrayList<>();
		count = 0;
		
		Map<String, Set<String>> temp = riskFunctionData.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			temp.entrySet().forEach(e -> {
				//System.out.println(e.getKey());
				log.info("Risk  :: " + e.getKey());
				Set<String> values = e.getValue();
				//values.forEach(System.out::println);
				// if (functions.size()<2);
				if (functions.containsAll(values)) {
					count = count + 1;
					String riskCode=e.getKey();
					log.info("================================count  +" +count);
					values.forEach(v -> {
						//functions.remove(v);
						confilcteds.add(new Confilcted("",v, riskCode, count));
						log.info("Function  :: " +v);
					});
					log.info("================================ +");
				}
			});


		// log.info(" RsikFinderEngine >> riskFind >> confilcteds size
		// "+confilcteds.size());
		return confilcteds;
	}
}
