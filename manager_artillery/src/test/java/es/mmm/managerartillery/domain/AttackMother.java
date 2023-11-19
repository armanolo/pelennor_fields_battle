package es.mmm.managerartillery.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.mmm.managerartillery.application.mapper.AttackMapper;
import es.mmm.managerartillery.infrastructure.dto.AttackRequest;
import es.mmm.managerartillery.infrastructure.dto.AttackResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class AttackMother {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final AttackMapper attackMapper = new AttackMapper();
    public static final Random random = new Random();

    public static List<String[]>  readAndSplitFile() {
        List<String[]> list = new ArrayList<>();
        try{
            Resource resource = new ClassPathResource("test_cases.txt");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    list.add(parts);
                }
            }

        }catch(Exception ignored){}
        return list;
    }

    public static Attack validAttack() {
        Attack attack = null;
        try {
            var list = readAndSplitFile();
            var index = random.nextInt(list.size()) + 1;
            attack = validAttack(index);

        }catch (Exception ignored){}
        return attack;
    }
    public static AttackResponse validAttackResponse() {
        AttackResponse attack = null;
        try {
            var list = readAndSplitFile();
            var index = random.nextInt(list.size()) + 1;
            attack = validAttackResponse(index);

        }catch (Exception ignored){}
        return attack;
    }

    public static Attack validAttack(int index) {
        Attack attack = null;
        try {
            var list = readAndSplitFile();
            AttackRequest attackRequest = objectMapper.readValue(list.get(index)[0], AttackRequest.class);
            attack = attackMapper.dtoToEntity(attackRequest);

        }catch (Exception ignored){}
        return attack;
    }
    public static AttackResponse validAttackResponse(int index) {
        AttackResponse attack = null;
        try {
            var list = readAndSplitFile();
            attack = objectMapper.readValue(list.get(index)[1], AttackResponse.class);

        }catch (Exception ignored){}
        return attack;
    }

}
