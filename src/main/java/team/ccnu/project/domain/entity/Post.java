package team.ccnu.project.domain.entity;

import java.util.LinkedList;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@Entity
public class Post {
    //PK
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sn")
    private long sn; //postID
    //FK
    @ManyToOne
    private User owner;
    //FK
    @OneToMany
    private LinkedList<Image> files = new LinkedList<>();


    //DATA COLUMN
    private Long uid; //boardID
    private String title;
    @Lob private String content;
    @ColumnDefault("0") private Long view;
    @ColumnDefault("'Y'")
    private char status;  //adoptive or not
}
