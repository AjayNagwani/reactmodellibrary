
Pod::Spec.new do |s|
  s.name         = "RNModelLibrary"
  s.version      = "1.0.0"
  s.summary      = "RNModelLibrary"
  s.description  = <<-DESC
                  RNModelLibrary
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNModelLibrary.git", :tag => "master" }
  s.source_files  = "RNModelLibrary/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  