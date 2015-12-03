class InvertedIndex < ActiveRecord::Base
    belongs_to :record
    belongs_to :keyword

end
