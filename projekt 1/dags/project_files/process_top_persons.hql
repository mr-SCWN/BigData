ADD JAR /usr/lib/hive-hcatalog/share/hcatalog/hive-hcatalog-core-3.1.3.jar;

DROP TABLE IF EXISTS mapreduce_output;
DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS top_persons;
DROP TABLE IF EXISTS output_json;

CREATE EXTERNAL TABLE mapreduce_output (
  nconst STRING,
  acting_count INT,
  directing_count INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE
LOCATION '${input_dir3}';

CREATE EXTERNAL TABLE persons (
  nconst STRING,
  primaryName STRING,
  birthYear STRING,
  deathYear STRING,
  primaryProfession STRING,
  knownForTitles STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE
LOCATION '${input_dir4}';

CREATE TABLE top_persons (
  primaryName STRING,
  role STRING,
  movies INT
);

INSERT OVERWRITE TABLE top_persons
SELECT primaryName, role, movies FROM (

  SELECT * FROM (
    SELECT
      p.primaryName,
      'actor' AS role,
      mo.acting_count AS movies
    FROM
      persons p
      JOIN mapreduce_output mo ON p.nconst = mo.nconst
    WHERE
      p.primaryProfession RLIKE '(?i)actor|actress'
    ORDER BY
      mo.acting_count DESC
    LIMIT 3
  ) AS top_actors

  UNION ALL

  SELECT * FROM (
    SELECT
      p.primaryName,
      'director' AS role,
      mo.directing_count AS movies
    FROM
      persons p
      JOIN mapreduce_output mo ON p.nconst = mo.nconst
    WHERE
      p.primaryProfession RLIKE '(?i)director'
    ORDER BY
      mo.directing_count DESC
    LIMIT 3
  ) AS top_directors

) combined_results;

CREATE EXTERNAL TABLE output_json (
  primaryName STRING,
  role STRING,
  movies INT
)
ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe'
STORED AS TEXTFILE
LOCATION '${output_dir6}';


INSERT OVERWRITE TABLE output_json
SELECT
  primaryName,
  role,
  movies
FROM
  top_persons;
