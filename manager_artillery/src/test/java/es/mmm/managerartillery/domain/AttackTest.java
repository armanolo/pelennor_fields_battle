package es.mmm.managerartillery.domain;

import es.mmm.managerartillery.infrastructure.dto.AttackResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class AttackTest {



    @ParameterizedTest(name = "Execute attack {index}")
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11,12})
    void testAllTheAttacks(int number) {
        Attack attack = AttackMother.validAttack(number);
        Target target = attack.getTargetToFire();
        AttackResponse attackResponse = AttackMother.validAttackResponse(number);
        assertThat(target.getCoordinates().getX()).isSameAs(attackResponse.targetResponse().x());
        assertThat(target.getCoordinates().getY()).isSameAs(attackResponse.targetResponse().y());
        assertThat(target.getEnemies().getNumber()).isSameAs(attackResponse.casualties());
    }


    @DisplayName("Execute one attack")
    @Test
    void testOnlyOne() {
        int position = 12;
        Attack attack = AttackMother.validAttack(position);
        Target target = attack.getTargetToFire();
        AttackResponse attackResponse = AttackMother.validAttackResponse(position);
        assertThat(target.getCoordinates().getX()).isSameAs(attackResponse.targetResponse().x());
        assertThat(target.getCoordinates().getY()).isSameAs(attackResponse.targetResponse().y());
        assertThat(target.getEnemies().getNumber()).isSameAs(attackResponse.casualties());
    }
}
