import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.math.MathContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepositoryHelper {

    private boolean _exists;
    private String _repositoryUrl;
    private String _repositoryName;
    private  Repository _gitRepository;

    public RepositoryHelper() throws IOException {
        this._gitRepository = getRepository();
        this._exists = false;
        if(this._gitRepository != null){
            this._exists = true;
        }

    }
    public Repository getRepository() throws IOException {
        File gitFolder = new File(".git");
        if(gitFolder.exists()) {
            Repository existingRepo = new FileRepositoryBuilder()
                    .setGitDir(gitFolder).build();
            return  existingRepo;
        }
        return  null;
    }
    public boolean exists(){
        return this._exists;
    }
    public  String get_repositoryUrl(){
        Config config = this._gitRepository.getConfig();
        this._repositoryUrl = config.getString( "remote", "origin", "url" );
        return this._repositoryUrl;
    }
    public  String getRepositoryName(){
        Pattern pattern = Pattern.compile("([^/]+)\\.git$");
        Matcher matcher = pattern.matcher(this._repositoryUrl);
        if(matcher.find()){
            String repoName = matcher.group();
            this._repositoryName = repoName.replace(".git","");
            return this._repositoryName;
        }
        return null;
    }
}
